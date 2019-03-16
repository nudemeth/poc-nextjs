/* eslint-disable no-console */
import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import Container from '../components/layout/Container'
import LoginCard from '../components/page/login/LoginCard'
import { storeUser } from '../actions/identity.actions'

class Login extends React.Component {
    constructor(props) {
        super(props)
    }

    static async getInitialProps({ ctx: { store, query } }) {
        if (query.user) {
            store.dispatch(storeUser(query.user))
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