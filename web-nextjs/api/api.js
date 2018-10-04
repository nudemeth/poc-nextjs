import fetch from 'isomorphic-unfetch'

function validateHostAndPort(host) {
    if (typeof host === 'string')
        return
    throw new Error('Host is not valid.')
}

class Api {
    constructor(host) {
        validateHostAndPort(host)
        this.host = host
    }

    tryFetch = async (url, opts) => {
        try {
            return await fetch(url, opts)
        } catch (err) {
            return await Promise.reject(err)
        }
    }
}

export default Api