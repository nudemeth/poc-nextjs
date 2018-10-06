import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import Container from '../components/layout/Container'
import { loadItem } from '../actions/catalog.actions'

class Item extends React.Component {
    constructor(props) {
        super(props)
    }

    static async getInitialProps({ ctx: { store, query }}) {
        const id = parseInt(query.id)
        store.dispatch(loadItem(id))
    }

    static propTypes = {
        dispatch: PropTypes.func.isRequired,
        url: PropTypes.object.isRequired,
        item: PropTypes.object.isRequired
    }

    componentDidMount() {
        const id = parseInt(this.props.url.query.id)
        this.props.dispatch(loadItem(id))
    }

    render() {
        const { item } = this.props
        return (
            <Container title='Item' header={<div/>}>
                <h1>Item Name: {item.name}</h1>
                <ul>
                    <li>CatalogType: {item.catalogType}</li>
                    <li>Create Date: {item.createDate}</li>
                    <li>Image URL: {item.imageUrl}</li>
                </ul>
            </Container>
        )
    }
}

const mapStateToProps = ({ itemReducer: { item }}) => ({ item })

export default connect(mapStateToProps)(Item)
export { Item }