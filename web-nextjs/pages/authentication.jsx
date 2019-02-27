import React from 'react'
//import PropTypes from 'prop-types'

class Authentication extends React.Component {
    constructor(props) {
        super(props)
    }

    /*static async getInitialProps({ ctx: { query }}) {
        if (query.code) {
            return { code: query.code }
        }
    }

    static propTypes = {
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