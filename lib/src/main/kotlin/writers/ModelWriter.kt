package writers

import models.IModel

class ModelWriter(private val principle: IModel) : ElementFactory(), IWriter {

    override fun write(): DocumentWithName {
        val root = writeModel()
        root.appendChild(writeRegistry())
        root.appendChild(createFromNode(principle))

        return DocumentWithName("${principle.name}.mpsr", document, "structure")
    }

    private fun writeModel() = ElementBuilder.build(document, "model") {
        attribute("ref", principle.reference)
        attribute("content", "root")
    }.build().apply { document.appendChild(this) }

    private fun writeRegistry() =
        ElementBuilder.build(document, "registry") {
            for (lang in principle.registry) {
                addChildNode("language") {
                    attribute("id", lang.uuid.toString())
                    attribute("name", lang.name)
                    for (concept in lang.concepts) {
                        addChildNode("concept") {
                            attribute("id", concept.conceptID)
                            attribute("name", "${lang.name}.${concept.name}")
                            attribute("flags", "ig") // no clue what this means
                            attribute("index", concept.conceptID)
                            for (property in concept.properties) {
                                addChildNode("property") {
                                    attribute("id", property.nodeID)
                                    attribute("name", property.role)
                                    attribute("index", "${concept.conceptID}:${property.nodeID}")
                                }
                            }
                            for (reference in concept.references) {
                                addChildNode("reference") {
                                    attribute("id", reference.nodeID)
                                    attribute("name", reference.role)
                                    attribute("index", "${concept.conceptID}:${reference.nodeID}")
                                }
                            }
                            for (childReference in concept.children) {
                                addChildNode("childReference") {
                                    attribute("id", childReference.nodeID)
                                    attribute("name", childReference.role)
                                    attribute("index", "${concept.conceptID}:${childReference.nodeID}")
                                }
                            }
                        }
                    }
                }
            }
        }.build()
}
