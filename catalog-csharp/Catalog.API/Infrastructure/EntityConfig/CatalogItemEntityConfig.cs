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
            builder.Property(c => c.CreatedDate).HasColumnName("CREATED_DATE");
            builder.Property(c => c.CatalogTypeId).HasColumnName("CATALOG_TYPE_ID");
            builder.Property(c => c.CatalogBrandId).HasColumnName("CATALOG_BRAND_ID");
            builder.HasOne(c => c.CatalogBrand).WithMany().HasForeignKey(c => c.CatalogBrandId);
            builder.HasOne(c => c.CatalogType).WithMany().HasForeignKey(c => c.CatalogTypeId);
            builder.Property(c => c.ImagePath).HasColumnName("IMAGE_PATH");
            builder.Ignore(c => c.ImageAlt);
            builder.Ignore(c => c.ImageUrl);
        }
    }
}