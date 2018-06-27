import React from 'react';
import Container from '../components/layout/Container';
import { loadCategories } from '../actions/category.actions';
import { withReduxSaga } from '../store/store';
import CategoryList from '../components/page/category/CategoryList';

class Category extends React.Component {
    constructor(props) {
        super(props);
    }

    static async getInitialProps({ store }) {
        store.dispatch(loadCategories());
    }

    componentDidMount() {
        this.props.dispatch(loadCategories());
    }

    render() {
        return (
            <Container title='Category'>
                <CategoryList/>
            </Container>
        );
    }
}

export default withReduxSaga()(Category);
export { Category };