using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Cors;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.HttpsPolicy;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using Autofac;

namespace Catalog.API
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        public void ConfigureContainer(ContainerBuilder builder)
        {
            builder.RegisterModule(new AutofacModule(Configuration));
        }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            var apiCORSConfig = GetAPIGatewayCORSConfig();
            
            services.AddCors(o => 
            {
                o.AddPolicy("APIGateway", b => b.WithOrigins(apiCORSConfig).AllowCredentials());
            });
            services.AddMvc().SetCompatibilityVersion(CompatibilityVersion.Version_2_1);
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IHostingEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            else
            {
                app.UseHsts();
            }

            app.UseHttpsRedirection();
            app.UseCors();
            app.UseMvc();
        }

        private string[] GetAPIGatewayCORSConfig()
        {
            var url = Configuration["Default_API_URL:HTTP"];
            var urlTLS = Configuration["Default_API_URL:HTTPS"];
            var apiURL = Configuration["API_BASE_URL"];
            var apiURLTLS = Configuration["API_BASE_URL_TLS"];

            Console.WriteLine("API Gateway URL: " + apiURL);
            
            if (!string.IsNullOrEmpty(apiURL))
                url = apiURL;
            
            if (!string.IsNullOrEmpty(apiURLTLS))
                urlTLS = apiURLTLS;

            if (string.IsNullOrEmpty(url) || string.IsNullOrEmpty(urlTLS))
                throw new Exception("No valid configuration found for api gateway.");

            return new string[] { url, urlTLS };
        }
    }
}
