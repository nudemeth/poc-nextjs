import React from 'react'

class Authentication extends React.Component {
    constructor(props) {
        super(props)
    }

    componentDidMount() {
        if (typeof window != 'undefined') {
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