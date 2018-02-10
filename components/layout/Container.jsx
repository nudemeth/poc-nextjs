import React from 'react';
import Head from 'next/head';
import Header from './Header'
import SideBar from './SideBar'
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
                </Head>
                <Header/>
                <SideBar/>
                {this.props.children}
            </Root>
        );
    }
}

export default Container;