import Api from './api'
import config from '../config'

class IdentityApi extends Api {
    constructor(host) {
        super(host)
    }

    getAccessToken = async (issuer, code) => {
        if (!issuer || !code) {
            Promise.reject(new Error('Issuer or code is empty'))
        }
        return await this.tryFetch(`${this.host}token?issuer=${issuer}&code=${code}`)
    }
}

export default new IdentityApi(config.api.identity.uri)