import React from 'react';
import { StyleSheet, ScrollView, Text } from 'react-native';

import Title from '../components/Title';
import ItemCard from '../components/ItemCard';

import generalinfoStyles from '../styles/generalinfo';

import * as labels from '../constants/description';
import itemCardTypes from '../enums/itemCardTypes';


const styles = StyleSheet.create({
  ...generalinfoStyles(),
});

const DescriptionScreen = () => {
  return (
    <ScrollView contentContainerStyle={styles.container}>
      <Title titleText='Sibipiruna' />
      <ItemCard
        type={itemCardTypes.LARGE_TEXT}
        title="Características físicas técnicas e executivas"
        value="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
      />
    </ScrollView>
  );
};

DescriptionScreen.navigationOptions = () => ({
  headerTitle: labels.DESCRIPTION,
});

export default DescriptionScreen;