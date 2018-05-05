import fetch from 'isomorphic-unfetch';
import Api from './api';

class ProductApi extends Api {
    constructor(host) {
        super(host);
    }

    async getProduct(id) {
        return await fetch(this.host + 'product/' + id);
    }
}

export default ProductApi;