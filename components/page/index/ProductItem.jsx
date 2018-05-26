import React from 'react';
import PropTypes from 'prop-types';
import GridList from '@material-ui/core/GridList';
import GridListTile from '@material-ui/core/GridListTile';
import GridListTileBar from '@material-ui/core/GridListTileBar';
import Icon from '@material-ui/core/Icon';
import IconButton from '@material-ui/core/IconButton';
import Typography from '@material-ui/core/Typography';
import { withStyles } from '@material-ui/core/styles';
import { connect } from 'react-redux';

const styles = theme => ({
    productTile: {
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#fff'
    },
    productRoot: {
        height: 184,
        [theme.breakpoints.up('lg')]: {
            width: '33.3333%',
            padding: 12
        },
        [theme.breakpoints.down('md')]: {
            width: '50%',
            padding: 8
        },
        [theme.breakpoints.down('xs')]: {
            width: '100%',
            padding: 2
        }
    },
    button: {
        color: '#fff'
    }
});

class ProductItem extends React.Component {
    constructor(props) {
        super(props);
    }

    static propTypes = {
        classes: PropTypes.object.isRequired,
        theme: PropTypes.object.isRequired,
        product: PropTypes.object.isRequired
    }

    render() {
        const { classes, product } = this.props;
        return (
            <GridListTile classes={{root: classes.productRoot, tile: classes.productTile}}>
                <img src={product.imageUrl} alt={product.imageAlt} />
                <GridListTileBar
                    title={product.name}
                    actionIcon={
                        <IconButton className={classes.button} aria-label="Add to shopping cart">
                            <Icon>add_shopping_cart</Icon>
                        </IconButton>
                    }
                />
            </GridListTile>
        );
    }
}

export default withStyles(styles, { withTheme: true })(ProductItem);
export { ProductItem };