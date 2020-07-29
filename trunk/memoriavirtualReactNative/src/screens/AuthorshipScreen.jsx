import React from 'react';
import { StyleSheet, View, ScrollView } from 'react-native';

import * as labels from '../constants/authorship';

import generalinfoStyles from '../styles/generalinfo';

import Title from '../components/Title';
import AuthorshipCard from '../components/AuthorshipCard';

const styles = StyleSheet.create({
  ...generalinfoStyles(),
});

const AuthorshipScreen = ({ navigation }) => {
  return (
    <View style={styles.container}>
      <Title titleText="Sibipiruna" />
      <ScrollView contentContainerStyle={styles.generalPurposeForm}>
        <AuthorshipCard author="IBM" authorshipKind="Autor Institucional" />
        <AuthorshipCard author="IBM" authorshipKind="Autor Institucional" />
        <AuthorshipCard author="IBM" authorshipKind="Autor Institucional" />
        <AuthorshipCard author="IBM" authorshipKind="Autor Institucional" />
        <AuthorshipCard author="IBM" authorshipKind="Autor Institucional" />
        <AuthorshipCard author="IBM" authorshipKind="Autor Institucional" />
        <AuthorshipCard author="IBM" authorshipKind="Autor Institucional" />
      </ScrollView>
    </View>
  );
};


AuthorshipScreen.navigationOptions = () => ({
  headerTitle: labels.AUTHORSHIP,
});

export default AuthorshipScreen;