using Autofac;
using Microsoft.Extensions.Logging;
using Catalog.API.Services;

namespace Catalog.API
{
    public class AutofacModule : Module
    {
        protected override void Load(ContainerBuilder builder)
        {
            // The generic ILogger<TCategoryName> service was added to the ServiceCollection by ASP.NET Core.
            // It was then registered with Autofac using the Populate method in ConfigureServices.
            builder.Register(c => new CatalogService(c.Resolve<ILogger<CatalogService>>()))
                .As<ICatalogService>()
                .InstancePerLifetimeScope();
        }
    }
}