import fetch from 'isomorphic-unfetch';

class ProductApi {
    async getProduct(id) {
        return await fetch('http://localhost:5000/product/' + id);
    }
}

export { ProductApi };