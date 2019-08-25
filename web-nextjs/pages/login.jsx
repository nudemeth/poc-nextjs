import React from 'react'
import PropTypes from 'prop-types'
import Router from 'next/router'
import { connect } from 'react-redux'
import Container from '../components/layout/Container'
import LoginCard from '../components/page/login/LoginCard'

class Login extends React.Component {
    constructor(props) {
        super(props)
    }

    static async getInitialProps({ ctx: { res, query, pathname } }) {
        if (!this.isRedirect(query, pathname)) {
            return	
        }
        this.redirect(res)	
    }

    static propTypes = {
        dispatch: PropTypes.func.isRequired
    }

    static redirect(res) {
        if (!res) {
            Router.push('/')
            return
        }

        res.writeHead(302, {
            Location: '/'
        })
        res.end()
    }

    static isRedirect(query, pathname) {
        return query.accessToken && pathname === '/login'
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