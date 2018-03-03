import React from 'react';
import PropTypes from 'prop-types';
import Card, { CardActions, CardContent, CardMedia, CardHeader } from 'material-ui/Card';
import Icon from 'material-ui/Icon';
import IconButton from 'material-ui/IconButton';
import Typography from 'material-ui/Typography';
import { withStyles } from 'material-ui/styles';

const styles = theme => ({
    card: {
        width: '100%'
    },
    media: {
        height: 200
    },
    actions: {
        display: 'flex'
    },
    shoppingCart: {
        marginLeft: 'auto'
    }
});

class ProductItem extends React.Component {
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
            <li>
                <Card className={classes.card}>
                    <CardHeader
                        title={this.props.title}
                        subheader={this.props.itemDate}
                    />
                    <CardMedia
                        className={classes.media}
                        image={this.props.imageUrl}
                        title={this.props.imageAlt}
                    />
                    <CardActions className={classes.actions} disableActionSpacing>
                        <IconButton color="primary" aria-label="Add to Favorite">
                            <Icon>favorite_border</Icon>
                        </IconButton>
                        <IconButton color="primary" aria-label="Share">
                            <Icon>share</Icon>
                        </IconButton>
                        <IconButton color="primary" aria-label="Add to shopping cart" className={classes.shoppingCart}>
                            <Icon>add_shopping_cart</Icon>
                        </IconButton>
                    </CardActions>
                </Card>
            </li>
        );
    }
}

export default withStyles(styles, { withTheme: true })(ProductItem);