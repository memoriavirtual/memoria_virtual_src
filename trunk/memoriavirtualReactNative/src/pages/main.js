import React, { Component } from 'react';
import { View, Text, Image, StyleSheet} from 'react-native';

export default class Main extends Component{

    static navigationOptions = {
        title: 'Memória Virtual',
    };

    render() {
        return (
            <View style = {styles.container}>
                <Text style = {styles.text_title}>Home</Text>
                <Image
                    style = {styles.img_title}
                    source = {{uri: 'https://avatars1.githubusercontent.com/u/12517676?s=200&v=4'}}
                />
            </View>
        );
    }
}
const styles = StyleSheet.create ({
    
    text_title: {
        paddingTop: 50,
        alignSelf: 'center',
        fontSize: 60
    },

    img_title: {
        marginTop: 70,
        alignSelf: 'center',
        width: 150,
        height: 150
    }

});