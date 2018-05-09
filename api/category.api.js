import fetch from 'isomorphic-unfetch';
import Api from './api';

class CategoryApi extends Api {
    constructor(host) {
        super(host);
    }

    getCategories = async () => {
        return await fetch(this.host + 'categories');
    }
}

export default new CategoryApi('http://localhost:5000/');