using Catalog.API.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace Catalog.API.Infrastructure.EntityConfig
{
    public class CatalogItemEntityConfig : IEntityTypeConfiguration<CatalogItem>
    {
        public void Configure(EntityTypeBuilder<CatalogItem> builder)
        {
            builder.ToTable("CATALOG_ITEM");
            builder.HasKey(c => c.Id);
            builder.Property(c => c.Id).IsRequired(true);
            builder.Property(c => c.Name).IsRequired(true).HasMaxLength(200);
            builder.Property(c => c.Price).IsRequired(true);
            builder.HasOne(c => c.CatalogBrand).WithMany().HasForeignKey(c => c.CatalogBrandId);
            builder.HasOne(c => c.CatalogType).WithMany().HasForeignKey(c => c.CatalogTypeId);
        }
    }
}