package nudemeth.poc.ordering.domain.model

trait RepositoryOperations[T <: AggregateRoot] {
  val unitOfWork: UnitOfWork
}
