/* eslint-disable no-console */
import React from 'react'
import { connect } from 'react-redux'
import Container from '../components/layout/Container'
import LoginCard from '../components/page/login/LoginCard'

class Login extends React.Component {
    constructor(props) {
        super(props)
    }

    static async getInitialProps({ ctx: { query } }) {
        if (query.token) {
            const token = query.token
            console.log(`Page Login token: ${token}`)
            return { token: token }
        }
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