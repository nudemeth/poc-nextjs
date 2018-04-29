import fetch from 'isomorphic-unfetch';

class ProductApi {
    async getProduct(id) {
        return fetch('http://localhost:5000/product/' + id);
    }
}

export { ProductApi };