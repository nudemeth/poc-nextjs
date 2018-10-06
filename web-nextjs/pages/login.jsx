import React from 'react'
import { connect } from 'react-redux'
import Container from '../components/layout/Container'
import LoginCard from '../components/page/login/LoginCard'

class Login extends React.Component {
    constructor(props) {
        super(props)
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