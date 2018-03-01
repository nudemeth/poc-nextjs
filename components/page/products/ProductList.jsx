import React from 'react';
import PropTypes from 'prop-types';
import Card, { CardActions, CardContent, CardMedia, CardHeader } from 'material-ui/Card';
import Icon from 'material-ui/Icon';
import IconButton from 'material-ui/IconButton';
import Typography from 'material-ui/Typography';
import { withStyles } from 'material-ui/styles';
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
        return (
            <ul className={classes.listContainer}>
                <ProductItem/>
                <ProductItem/>
                <ProductItem/>
                <ProductItem/>
                <ProductItem/>
            </ul>
        );
    }
}

export default withStyles(styles, { withTheme: true })(ProductList);