using Autofac;
using Microsoft.Extensions.Logging;
using Catalog.API.Services;
using System.Collections.Generic;

namespace Catalog.API
{
    public class AutofacModule : Module
    {
        protected override void Load(ContainerBuilder builder)
        {
            var config = new Dictionary<string, string>()
            {
                { "image-path", "./Images" }
            };

            // The generic ILogger<TCategoryName> service was added to the ServiceCollection by ASP.NET Core.
            // It was then registered with Autofac using the Populate method in ConfigureServices.
            builder.Register(c => new CatalogService(c.Resolve<ILogger<CatalogService>>()))
                .As<ICatalogService>()
                .InstancePerLifetimeScope();

            builder.Register(c => config)
                .As<IDictionary<string, string>>()
                .SingleInstance();
        }
    }
}