import React, { Component, useState } from 'react';
import {
  View,
  Text,
  Image,
  StyleSheet,
  TextInput,
} from 'react-native';

import generalinfoStyles from '../styles/generalinfo';
import CustomTextInfo from '../components/CustomTextInfo';

const styles = StyleSheet.create({
  ...generalinfoStyles(),
});


const footnoteFirst = 'Copyright © 2009 LABES';
const footnoteSecond = 'Laboratório de Engenharia de Software - ICMC-USP';

const Disponibility = () => {

  return (
    <View style={styles.container}>
      <View style = {styles.title}>
        <Text style={{fontSize:26}}>Sibipiruna2</Text>
      </View>
      <View style={styles.form}>

          <CustomTextInfo
            infoTitle="Disponibilidade"
            infoValue="-"
          />

          <CustomTextInfo
            infoTitle="Condições de acesso"
            infoValue="-"
          />

          <CustomTextInfo
            infoTitle="Condições de reprodução"
            infoValue="-"
          />

          <CustomTextInfo
            infoTitle="Proteção"
            infoValue="-"
          />

          <CustomTextInfo
            infoTitle="Instituição Protetora"
            infoValue="-"
          />

          <CustomTextInfo
            infoTitle="Nº de processo"
            infoValue="-"
          />

          <CustomTextInfo
            infoTitle="Data de retorno"
            infoValue="-"
          />

          <View style={styles.inputs}>

            <View style={styles.lastFormTitle}>
              <Text style={{fontSize:18}}
              >Uso e aproveitamento</Text>
            </View>
            <View
            style={styles.spacer}
            />
            <View style={styles.formInput}>
              <TextInput
                value="-"
              />
            </View>

          </View>

      </View>

    <Text style={styles.footnoteText}>{footnoteFirst}{'\n'}{footnoteSecond}</Text>
    </View>
  );
};

Disponibility.navigationOptions = () => ({
  headerTitle: 'Disponibilidade',
  headerTitleStyle: {
    color: 'white',
    fontWeight: 'bold',
    fontSize: 22,
  },
  headerStyle: {
    backgroundColor: '#4488BB',
  },
});

export default Disponibility;
