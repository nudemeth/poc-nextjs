import React from 'react'
import PropTypes from 'prop-types'
import Router from 'next/router'
import { connect } from 'react-redux'
import Container from '../components/layout/Container'
import Catalog from '../components/page/index/Catalog'
import HeaderContent from '../components/page/index/HeaderContent'
import { loadItems } from '../actions/catalog.actions'

class Index extends React.Component {
    constructor(props) {
        super(props)
    }

    static async getInitialProps({ ctx: { store, res, query } }) {
        if (!this.isRedirect(query)) {
            store.dispatch(loadItems())
            return
        }

        this.redirect(res)
    }

    static propTypes = {
        dispatch: PropTypes.func.isRequired
    }

    static redirect(res) {
        if (res) {
            res.writeHead(302, {
                Location: '/login'
            })
            res.end()
        } else {
            Router.push('/login')
        }
    }

    static isRedirect(query) {
        return !query.accessToken
    }

    componentDidMount() {
        this.props.dispatch(loadItems())
    }

    render() {
        return (
            <Container title='Index' header={<HeaderContent/>}>
                <Catalog/>
            </Container>
        )
    }
}

export default connect()(Index)
export { Index }