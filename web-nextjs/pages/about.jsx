import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import Container from '../components/layout/Container'
import { initGreeting, updateGreeting } from '../actions/about.actions'

class About extends React.Component {
    constructor(props) {
        super(props)
    }

    static async getInitialProps({ ctx: { store } }) {
        store.dispatch(initGreeting('This is from server'))
        return {}
    }

    static propTypes = {
        dispatch: PropTypes.func.isRequired,
        greeting: PropTypes.string.isRequired
    }

    componentDidMount() {
        this.props.dispatch(updateGreeting())
    }

    render() {
        return (
            <Container title='About' header={<div/>}>
                <h1>This is About Page: {this.props.greeting}</h1>
            </Container>
        )
    }
}

const mapStateToProps = ({ aboutReducer: { greeting }}) => ({ greeting })

export default connect(mapStateToProps)(About)
export { About }