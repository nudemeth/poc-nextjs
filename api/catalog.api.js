import fetch from 'isomorphic-unfetch';
import Api from './api';

class CatalogApi extends Api {
    constructor(host) {
        super(host);
    }

    getItemById = async (id) => {
        return await fetch(this.host + 'items/' + id);
    }

    getItems = async (catalogTypeIds = [], catalogBrandIds = []) => {
        const catalogTypesParam = catalogTypeIds.join();
        const catalogBrandsParam = catalogBrandIds.join();

        if (catalogTypesParam !== "" && catalogBrandsParam !== "") {
            return await fetch(this.host + 'items/types/' + catalogTypesParam + '/brands/' + catalogBrandsParam);
        }

        if (catalogTypesParam !== "") {
            return await fetch(this.host + 'items/types/' + catalogTypesParam);
        }

        if (catalogBrandsParam !== "") {
            return await fetch(this.host + 'items/brands/' + catalogBrandsParam);
        }

        return await fetch(this.host + 'items');
    }

    getCatalogTypes = async () => {
        return await fetch(this.host + 'catalogTypes');
    }

    getCatalogBrands = async () => {
        return await fetch(this.host + 'catalogBrands');
    }
}

export default new CatalogApi('http://localhost:5000/catalog/');