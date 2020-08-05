import React, { useContext } from 'react';
import { StyleSheet, ScrollView, Text } from 'react-native';

import Title from '../components/Title';
import ItemCard from '../components/ItemCard';

import generalinfoStyles from '../styles/generalinfo';

import * as labels from '../constants/description';
import itemCardTypes from '../enums/itemCardTypes';

import { Context as I18nContext } from '../context/I18nContext';

const styles = StyleSheet.create({
  ...generalinfoStyles(),
});

const DescriptionScreen = () => {
  const { state: { translate } } = useContext(I18nContext);

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <Title titleText='Sibipiruna' />
      <ItemCard
        type={itemCardTypes.LARGE_TEXT}
        title={translate('DESCRIPTION_CHARACTERISTICS')}
        value="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
      />
    </ScrollView>
  );
};

DescriptionScreen.navigationOptions = () => ({
  headerTitle: labels.DESCRIPTION,
});

export default DescriptionScreen;