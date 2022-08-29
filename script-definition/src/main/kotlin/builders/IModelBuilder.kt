package builders

import models.IModel

interface IModelBuilder<T : IModel> {
    val subject: T
}