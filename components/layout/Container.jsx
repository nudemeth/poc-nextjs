import React from 'react';
import Head from 'next/head';
import Header from './Header.jsx'
import SideBar from './SideBar.jsx'
import uuidv4 from 'uuid/v4';
import Root from '../common/Root';

class Container extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <Root>
                <Head>
                    <title>{this.props.title}</title>
                </Head>,
                <Header key={uuidv4()} />,
                <SideBar key={uuidv4()} />,
                {this.props.children}
            </Root>
        );
    }
}

export default Container;