import fetch from 'isomorphic-unfetch';
import Api from './api';

class ItemApi extends Api {
    constructor(host) {
        super(host);
    }

    getItem = async (id) => {
        return await fetch(this.host + 'item/' + id);
    }

    getItems = async (categories) => {
        const query = categories.reduce((p, c) => p + 'categoryId=' + c + '&', '');
        return await fetch(this.host + 'items?' + query);
    }
}

export default new ItemApi('http://localhost:5000/');