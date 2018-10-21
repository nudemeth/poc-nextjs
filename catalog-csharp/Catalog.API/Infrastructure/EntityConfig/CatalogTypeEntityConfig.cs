using Catalog.API.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace Catalog.API.Infrastructure.EntityConfig
{
    public class CatalogTypeEntityConfig : IEntityTypeConfiguration<CatalogType>
    {
        public void Configure(EntityTypeBuilder<CatalogType> builder)
        {
            builder.ToTable("CATALOG_TYPE");
            builder.HasKey(c => c.Id);
            builder.Property(c => c.Id).IsRequired(true);
            builder.Property(c => c.Type).IsRequired(true).HasMaxLength(200);
            builder.Property(c => c.Icon).HasMaxLength(50);
            builder.Ignore(c => c.IsSelected);
        }
    }
}