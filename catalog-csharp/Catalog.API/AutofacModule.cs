using Autofac;
using Catalog.API.DataAccess;
using Catalog.API.Infrastructure;
using Catalog.API.Services;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
using System;
using System.Collections.Generic;

namespace Catalog.API
{
    public class AutofacModule : Module
    {
        private readonly IConfiguration configuration;
        public AutofacModule(IConfiguration configuration)
        {
            this.configuration = configuration;
        }
        protected override void Load(ContainerBuilder builder)
        {
            var connStr = GetConnectionString();
            var options = new DbContextOptionsBuilder<CatalogContext>().UseSqlServer(connStr).Options;
            // The generic ILogger<TCategoryName> service was added to the ServiceCollection by ASP.NET Core.
            // It was then registered with Autofac using the Populate method in ConfigureServices.
            builder.Register(c =>
                new UnitOfWork(new CatalogContext(options))
            )
            .As<IUnitOfWork>()
            .InstancePerLifetimeScope();

            builder.Register(c =>
                new CatalogService(
                    c.Resolve<ILogger<CatalogService>>(),
                    c.Resolve<IUnitOfWork>()
                )
            )
            .As<ICatalogService>()
            .InstancePerLifetimeScope();
        }

        private string GetConnectionString()
        {
            var connStr = configuration["ConnectionString"] ?? configuration["CONNECTION_STRING"];
            
            if (string.IsNullOrEmpty(connStr))
                connStr = configuration["CONNECTION_STRING"];

            if (string.IsNullOrEmpty(connStr))
                throw new Exception("No valid connection string has been found.");

            return connStr;
        }
    }
}