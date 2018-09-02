using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Cors;
using Catalog.API.Services;

namespace Catalog.API.Controllers
{
    public class ImageController : Controller
    {   
        private ICatalogService catalogService;
        private IDictionary<string, string> config;
        public ImageController(ICatalogService catalogService, IDictionary<string, string> config)
        {
            this.catalogService = catalogService;
            this.config = config;
        }

        [HttpGet]
        [Route("api/v1/catalog/items/{catalogItemId:int}/img")]
        [ProducesResponseType((int)HttpStatusCode.NotFound)]
        [ProducesResponseType((int)HttpStatusCode.BadRequest)]
        public async Task<IActionResult> GetImage(int catalogItemId)
        {
            if (catalogItemId <= 0)
            {
                return await Task.FromResult(BadRequest());
            }

            var item = catalogService.GetItems().SingleOrDefault(i => i.Id == catalogItemId);

            if (item == null)
            {
                return await Task.FromResult(NotFound());
            }

            var path = Path.Combine(config["image-path"], item.FileName);
            var imageFileExtension = Path.GetExtension(item.FileName);
            var mimetype = GetImageMimeTypeFromImageFileExtension(imageFileExtension);
            var buffer = System.IO.File.ReadAllBytes(path);

            return await Task.FromResult(File(buffer, mimetype));
        }

        private string GetImageMimeTypeFromImageFileExtension(string extension)
        {
            string mimetype;

            switch (extension)
            {
                case ".png":
                    mimetype = "image/png";
                    break;
                case ".gif":
                    mimetype = "image/gif";
                    break;
                case ".jpg":
                case ".jpeg":
                    mimetype = "image/jpeg";
                    break;
                case ".bmp":
                    mimetype = "image/bmp";
                    break;
                case ".tiff":
                    mimetype = "image/tiff";
                    break;
                case ".wmf":
                    mimetype = "image/wmf";
                    break;
                case ".jp2":
                    mimetype = "image/jp2";
                    break;
                case ".svg":
                    mimetype = "image/svg+xml";
                    break;
                default:
                    mimetype = "application/octet-stream";
                    break;
            }

            return mimetype;
        }
    }
}