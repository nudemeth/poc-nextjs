import fetch from 'isomorphic-unfetch';
import Api from './api';

class CatalogApi extends Api {
    constructor(host) {
        super(host);
    }

    getItemById = async (id) => {
        return await fetch(this.host + 'items/' + id);
    }

    getItems = async (categories) => {
        const query = categories.reduce((p, c) => p + 'categoryId=' + c + '&', '');
        return await fetch(this.host + 'items?' + query);
    }
}

export default new CatalogApi('http://localhost:5000/');