import Api from './api'
import config from '../config'

class IdentityApi extends Api {
    constructor(host) {
        super(host)
    }

    getAccessToken = async (issuer, code) => {
        console.log(code)
        console.log(issuer)
        if (!issuer || !code) {
            Promise.reject(new Error('Issuer or code is empty'))
        }
        return await this.tryFetch(`${this.host}token/${issuer}?code=${code}`)
    }
}

export default new IdentityApi(config.api.identity.uri)