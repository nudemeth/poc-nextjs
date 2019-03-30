/* eslint-disable no-console */
import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import Container from '../components/layout/Container'
import LoginCard from '../components/page/login/LoginCard'
import { storeUser, storeAuthSites } from '../actions/identity.actions'

class Login extends React.Component {
    constructor(props) {
        super(props)
    }

    static async getInitialProps({ ctx: { store, query } }) {
        if (query.user) {
            store.dispatch(storeUser(query.user))
        }
        if (query.sites) {
            store.dispatch(storeAuthSites(query.sites))
        }
    }

    static propTypes = {
        dispatch: PropTypes.func.isRequired
    }

    render() {
        return (
            <Container title='Login' header={<div/>}>
                <LoginCard />
            </Container>
        )
    }
}

export default connect()(Login)
export { Login }