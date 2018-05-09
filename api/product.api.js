import fetch from 'isomorphic-unfetch';
import Api from './api';

class ProductApi extends Api {
    constructor(host) {
        super(host);
    }

    getProduct = async (id) => {
        return await fetch(this.host + 'product/' + id);
    }

    getProducts = async (categories) => {
        const query = categories.reduce((p, c) => p + 'categoryId=' + c + '&', '');
        return await fetch(this.host + 'products?' + query);
    }
}

export default new ProductApi('http://localhost:5000/');