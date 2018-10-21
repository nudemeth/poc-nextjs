using Catalog.API.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace Catalog.API.Infrastructure.EntityConfig
{
    public class CatalogBrandEntityConfig : IEntityTypeConfiguration<CatalogBrand>
    {
        public void Configure(EntityTypeBuilder<CatalogBrand> builder)
        {
            builder.ToTable("CATALOG_BRAND");
            builder.HasKey(c => c.Id);
            builder.Property(c => c.Id).IsRequired(true);
            builder.Property(c => c.Brand).IsRequired(true).HasMaxLength(200);
            builder.Property(c => c.Icon).HasMaxLength(50);
            builder.Ignore(c => c.IsSelected);
        }
    }
}