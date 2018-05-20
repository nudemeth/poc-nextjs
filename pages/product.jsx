import React from 'react';
import Container from '../components/layout/Container';
import { withReduxSaga } from '../store/store';
import { loadProduct } from '../actions/product.actions';

class Product extends React.Component {
    constructor(props) {
        super(props);
    }

    static async getInitialProps({ store, query }) {
        const id = parseInt(query.id);
        store.dispatch(loadProduct(id));
    }

    componentDidMount() {
        const id = parseInt(this.props.url.query.id);
        this.props.dispatch(loadProduct(id));
    }

    render() {
        const { product } = this.props;
        return (
            <Container title='Product'>
                <h1>Product Name: {product.name}</h1>
                <ul>
                    <li>Category: {product.category}</li>
                    <li>Create Date: {product.createDate}</li>
                    <li>Image URL: {product.imageUrl}</li>
                </ul>
            </Container>
        );
    }
}

const mapStateToProps = ({ productReducer: { product }}) => ({ product });

export default withReduxSaga(mapStateToProps)(Product);
export { Product };