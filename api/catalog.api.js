import fetch from 'isomorphic-unfetch';
import Api from './api';

class CatalogApi extends Api {
    constructor(host) {
        super(host);
    }

    getItemById = async (id) => {
        return await fetch(this.host + 'items/' + id);
    }

    getItems = async (catalogTypes = [], catalogBrands = []) => {
        const query = catalogTypes.reduce((p, c) => p + 'catalogTypeId=' + c + '&', '');
        return await fetch(this.host + 'items?' + query);
    }

    getCatalogTypes = async () => {
        return await fetch(this.host + 'catalogTypes');
    }

    getCatalogBrands = async () => {
        return await fetch(this.host + 'catalogBrands');
    }
}

export default new CatalogApi('http://localhost:5000/');