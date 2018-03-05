import React from 'react';
import fetch from 'isomorphic-unfetch';
import Container from '../components/layout/Container';
import ProductList from '../components/page/products/ProductList';
import { loadProducts } from '../actions';
import { withReduxSaga } from '../store';

class Products extends React.Component {
    constructor(props) {
        super(props);
    }

    static async getInitialProps({ store }) {
        store.dispatch(loadProducts());
        return { text: "Products Page" };
    }

    componentDidMount() {
        this.props.dispatch(loadProducts());
    }

    render() {
        return (
            <Container title='Products'>
                <h1>Products Page: {this.props.text}</h1>
                <ProductList/>
            </Container>
        );
    }
}

const mapStateToProps = ({ products, error }) => ({ products, error })

export default withReduxSaga(mapStateToProps)(Products);
export { Products };