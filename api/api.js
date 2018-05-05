function validateHostAndPort(host, port) {
    if (typeof host === 'string')
        return;
    throw new Error('Host or Port are not valid.');
}

class Api {
    constructor(host) {
        validateHostAndPort(host);
        this.host = host;
    }
}

export default Api;