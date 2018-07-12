import fetch from 'isomorphic-unfetch';
import Api from './api';

class CatalogTypeApi extends Api {
    constructor(host) {
        super(host);
    }

    getCatalogTypes = async () => {
        return await fetch(this.host + 'catalogTypes');
    }
}

export default new CatalogTypeApi('http://localhost:5000/');