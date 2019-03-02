import React from 'react'
//import PropTypes from 'prop-types'
import { getAccessToken } from '../actions/identity.actions'

class Authentication extends React.Component {
    constructor(props) {
        super(props)
    }

    static async getInitialProps({ ctx: { store, query }}) {
        store.dispatch(getAccessToken(query.issuer, query.code))
    }

    /*static propTypes = {
        code: PropTypes.string.isRequired
    }*/

    componentDidMount() {
        if (typeof window != 'undefined') {
            //this.props.code
            window.opener.location.reload()
            window.close()
        }
    }

    render() {
        return null
    }
}

export default Authentication
export { Authentication }