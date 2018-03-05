import React from 'react';
import PropTypes from 'prop-types';
import Card, { CardActions, CardContent, CardMedia, CardHeader } from 'material-ui/Card';
import Icon from 'material-ui/Icon';
import IconButton from 'material-ui/IconButton';
import Typography from 'material-ui/Typography';
import { withStyles } from 'material-ui/styles';
import { connect } from 'react-redux';
import { loadProducts } from '../../../actions';
import ProductItem from './ProductItem';

const styles = theme => ({
    listContainer: {
        listStyle: 'none',
        display: 'grid',
        gridTemplateColumns: 'repeat(auto-fit, 300px)',
        gridGap: '2rem',
        justifyContent: 'space-around',
        padding: 0
    }
});

class ProductList extends React.Component {
    constructor(props) {
        super(props);
    }

    static propTypes = {
        classes: PropTypes.object.isRequired,
        theme: PropTypes.object.isRequired
    }

    render() {
        const { classes } = this.props;
        console.log('data' + this.props.products);
        console.log('error' + this.props.error);
        return (
            <ul className={classes.listContainer}>
                <ProductItem title="Item 1" itemDate="September 14, 2017" imageUrl="https://material-ui-next.com/static/images/cards/paella.jpg" imageAlt="Contemplative Reptile" />
                <ProductItem title="Item 2" itemDate="September 14, 2017" imageUrl="https://material-ui-next.com/static/images/cards/paella.jpg" imageAlt="Contemplative Reptile"/>
                <ProductItem title="Item 3" itemDate="September 14, 2017" imageUrl="https://material-ui-next.com/static/images/cards/paella.jpg" imageAlt="Contemplative Reptile"/>
                <ProductItem title="Item 4" itemDate="September 14, 2017" imageUrl="https://material-ui-next.com/static/images/cards/paella.jpg" imageAlt="Contemplative Reptile"/>
                <ProductItem title="Item 5" itemDate="September 14, 2017" imageUrl="https://material-ui-next.com/static/images/cards/paella.jpg" imageAlt="Contemplative Reptile"/>
            </ul>
        );
    }
}

const mapStateToProps = ({ products, error }) => ({ products, error });

export default connect(mapStateToProps)(withStyles(styles, { withTheme: true })(ProductList));