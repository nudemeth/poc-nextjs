namespace Catalog.EventBus
{
    public interface IIntegrationEventHandler<T> where T : IntegrationEvent
    {
    }
}