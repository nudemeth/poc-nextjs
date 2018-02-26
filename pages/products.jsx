import React from 'react';
import fetch from 'isomorphic-unfetch';
import Container from '../components/layout/Container';
import ProductList from '../components/page/products/ProductList';
import { withReduxSaga } from '../store';

class Products extends React.Component {
    constructor(props) {
        super(props);
    }

    static async getInitialProps({ req }) {
        //const res = await fetch('http://localhost:5000/api/v1/about');
        //const data = await res.json();

        return { text: "Products Page" };
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

export default withReduxSaga()(Products);
export { About };