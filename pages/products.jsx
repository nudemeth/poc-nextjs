import React from 'react';
import fetch from 'isomorphic-unfetch';
import Container from '../components/layout/Container';
import ProductList from '../components/page/products/ProductList';
import HeaderBar from '../components/page/products/HeaderBar';
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
            <Container title='Products' header={<HeaderBar/>}>
                <h1>Products Page: {this.props.text}</h1>
                <ProductList/>
            </Container>
        );
    }
}

export default withReduxSaga()(Products);
export { Products };