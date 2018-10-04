import React from 'react'
import PropTypes from 'prop-types'
import GridListTile from '@material-ui/core/GridListTile'
import GridListTileBar from '@material-ui/core/GridListTileBar'
import Icon from '@material-ui/core/Icon'
import IconButton from '@material-ui/core/IconButton'
import { withStyles } from '@material-ui/core/styles'
import config from '../../../config'

const styles = theme => ({
    itemTile: {
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#fff'
    },
    itemRoot: {
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
})

class Item extends React.Component {
    constructor(props) {
        super(props)
    }

    static propTypes = {
        classes: PropTypes.object.isRequired,
        theme: PropTypes.object.isRequired,
        item: PropTypes.object.isRequired
    }

    render() {
        const { classes, item } = this.props
        const imageUrl = `${config.api.catalog.uri}items/${item.id}/img`
        return (
            <GridListTile classes={{root: classes.itemRoot, tile: classes.itemTile}}>
                <img src={imageUrl} alt={item.name} />
                <GridListTileBar
                    title={item.name}
                    actionIcon={
                        <IconButton className={classes.button} aria-label='Add to shopping cart'>
                            <Icon>add_shopping_cart</Icon>
                        </IconButton>
                    }
                />
            </GridListTile>
        )
    }
}

export default withStyles(styles, { withTheme: true })(Item)
export { Item }