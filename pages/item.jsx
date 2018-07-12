import React from 'react';
import Container from '../components/layout/Container';
import { withReduxSaga } from '../store/store';
import { loadItem } from '../actions/catalog.actions';

class Item extends React.Component {
    constructor(props) {
        super(props);
    }

    static async getInitialProps({ store, query }) {
        const id = parseInt(query.id);
        store.dispatch(loadItem(id));
    }

    componentDidMount() {
        const id = parseInt(this.props.url.query.id);
        this.props.dispatch(loadItem(id));
    }

    render() {
        const { item } = this.props;
        return (
            <Container title='Item'>
                <h1>Item Name: {item.name}</h1>
                <ul>
                    <li>CatalogType: {item.catalogType}</li>
                    <li>Create Date: {item.createDate}</li>
                    <li>Image URL: {item.imageUrl}</li>
                </ul>
            </Container>
        );
    }
}

const mapStateToProps = ({ itemReducer: { item }}) => ({ item });

export default withReduxSaga(mapStateToProps)(Item);
export { Item };